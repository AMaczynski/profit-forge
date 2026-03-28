import { useState, useCallback, useMemo, useEffect } from 'react';
import type { Node, Edge, OnConnect, NodeChange, EdgeChange } from '@xyflow/react';
import { applyNodeChanges, applyEdgeChanges } from '@xyflow/react';
import type { NodeData, EdgeData, PartyType } from '../types';
import { partyApi } from '../api/partyApi';
import type { EmployeeResponse, PartyRelationshipResponse } from '../api/partyApi';
import GraphCanvas from '../components/GraphCanvas';
import NodeDetailPanel from '../components/NodeDetailPanel';
import AddNodeForm from '../components/AddNodeForm';
import AddEdgeForm from '../components/AddEdgeForm';
import styles from './GraphPage.module.css';

const POSITIONS_KEY = 'party-graph-positions';

function loadPositions(): Record<string, { x: number; y: number }> {
  try {
    const stored = localStorage.getItem(POSITIONS_KEY);
    return stored ? JSON.parse(stored) : {};
  } catch {
    return {};
  }
}

function savePositions(nodes: Node<NodeData>[]) {
  const positions = Object.fromEntries(nodes.map(n => [n.id, n.position]));
  localStorage.setItem(POSITIONS_KEY, JSON.stringify(positions));
}

function getDefaultPosition(index: number): { x: number; y: number } {
  const cols = 3;
  return {
    x: (index % cols) * 280 + 60,
    y: Math.floor(index / cols) * 160 + 60,
  };
}

function partyToNode(party: EmployeeResponse, index: number, savedPositions: Record<string, { x: number; y: number }>): Node<NodeData> {
  return {
    id: party.id,
    position: savedPositions[party.id] ?? getDefaultPosition(index),
    data: { name: party.name, email: party.email, partyType: party.type as PartyType },
    type: 'custom',
  };
}

function relationshipToEdge(rel: PartyRelationshipResponse): Edge<EdgeData> {
  return {
    id: rel.id,
    source: rel.fromPartyId,
    target: rel.toPartyId,
    label: `${rel.fromRole} → ${rel.toRole}: ${rel.relationshipName}`,
    data: { name: rel.relationshipName, fromRole: rel.fromRole, toRole: rel.toRole },
  };
}

export default function GraphPage() {
  const [nodes, setNodes] = useState<Node<NodeData>[]>([]);
  const [edges, setEdges] = useState<Edge<EdgeData>[]>([]);
  const [selectedNode, setSelectedNode] = useState<Node<NodeData> | null>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const savedPositions = loadPositions();
    Promise.all([partyApi.getAllParties(), partyApi.getAllRelationships()])
      .then(([parties, relationships]) => {
        setNodes(parties.map((p, i) => partyToNode(p, i, savedPositions)));
        setEdges(relationships.map(relationshipToEdge));
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    if (nodes.length > 0) savePositions(nodes);
  }, [nodes]);

  const displayNodes = useMemo(() => {
    const query = searchQuery.trim().toLowerCase();
    if (!query) return nodes;
    return nodes.map(node => {
      const matches = node.data.name.toLowerCase().includes(query);
      return { ...node, style: { ...node.style, opacity: matches ? 1 : 0.2 } };
    });
  }, [nodes, searchQuery]);

  const onNodesChange = useCallback(
    (changes: NodeChange[]) => setNodes(nds => applyNodeChanges(changes, nds) as Node<NodeData>[]),
    []
  );

  const onEdgesChange = useCallback(
    (changes: EdgeChange[]) => setEdges(eds => applyEdgeChanges(changes, eds) as Edge<EdgeData>[]),
    []
  );

  const onConnect: OnConnect = useCallback(() => {}, []);

  const handleNodeClick = useCallback((node: Node<NodeData>) => {
    setSelectedNode(node);
  }, []);

  async function handleAddNode(partyType: PartyType, name: string, email: string) {
    try {
      const create = partyType === 'SeniorManager'
        ? partyApi.createSeniorManager
        : partyType === 'RegionalManager'
          ? partyApi.createRegionalManager
          : partyApi.createInsuranceAdvisor;

      const party = await create({ name, email });
      setNodes(prev => [...prev, partyToNode(party, prev.length, loadPositions())]);
    } catch (err) {
      alert(`Failed to create party: ${err instanceof Error ? err.message : err}`);
    }
  }

  async function handleAddEdge(fromPartyId: string, fromRole: string, toPartyId: string, toRole: string, relationshipName: string) {
    try {
      const rel = await partyApi.assignRelationship({ fromPartyId, fromRole, toPartyId, toRole, relationshipName });
      setEdges(prev => [...prev, relationshipToEdge(rel)]);
    } catch (err) {
      alert(`Failed to create relationship: ${err instanceof Error ? err.message : err}`);
    }
  }

  if (loading) return <div className={styles.page} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>Loading…</div>;
  if (error) return <div className={styles.page} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'red' }}>Error: {error}</div>;

  return (
    <div className={styles.page}>
      <div className={styles.sidebar}>
        <div className={styles.logo}>Graph Visualizer</div>
        <input
          className={styles.search}
          type="search"
          placeholder="Search nodes by name…"
          value={searchQuery}
          onChange={e => setSearchQuery(e.target.value)}
        />
        <AddNodeForm onAdd={handleAddNode} />
        <AddEdgeForm nodes={nodes} onAdd={handleAddEdge} />
      </div>
      <div className={styles.canvas}>
        <GraphCanvas
          nodes={displayNodes}
          edges={edges}
          onNodesChange={onNodesChange}
          onEdgesChange={onEdgesChange}
          onConnect={onConnect}
          onNodeClick={handleNodeClick}
        />
        <NodeDetailPanel node={selectedNode} onClose={() => setSelectedNode(null)} />
      </div>
    </div>
  );
}
