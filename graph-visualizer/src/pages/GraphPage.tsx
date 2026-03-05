import { useState, useCallback, useMemo } from 'react';
import type { Node, Edge, OnConnect, NodeChange, EdgeChange } from '@xyflow/react';
import { addEdge, applyNodeChanges, applyEdgeChanges } from '@xyflow/react';
import type { NodeData, EdgeData } from '../types';
import { initialNodes, initialEdges } from '../sampleData';
import GraphCanvas from '../components/GraphCanvas';
import NodeDetailPanel from '../components/NodeDetailPanel';
import AddNodeForm from '../components/AddNodeForm';
import AddEdgeForm from '../components/AddEdgeForm';
import styles from './GraphPage.module.css';

export default function GraphPage() {
  const [nodes, setNodes] = useState<Node<NodeData>[]>(initialNodes);
  const [edges, setEdges] = useState<Edge<EdgeData>[]>(initialEdges);
  const [selectedNode, setSelectedNode] = useState<Node<NodeData> | null>(null);
  const [searchQuery, setSearchQuery] = useState('');

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

  const onConnect: OnConnect = useCallback(
    (connection) => {
      const name = prompt('Edge name:') ?? 'connects to';
      setEdges(eds => addEdge({ ...connection, label: name, data: { name }, type: 'custom' }, eds) as Edge<EdgeData>[]);
    },
    []
  );

  const handleNodeClick = useCallback((node: Node<NodeData>) => {
    setSelectedNode(node);
  }, []);

  function handleAddNode(data: NodeData) {
    const id = `node-${Date.now()}`;
    const newNode: Node<NodeData> = {
      id,
      position: { x: Math.random() * 400 + 100, y: Math.random() * 300 + 100 },
      data,
      type: 'custom',
    };
    setNodes(prev => [...prev, newNode]);
  }

  function handleAddEdge(source: string, target: string, name: string) {
    const id = `edge-${Date.now()}`;
    const newEdge: Edge<EdgeData> = {
      id,
      source,
      target,
      label: name,
      data: { name },
      type: 'custom',
    };
    setEdges(prev => [...prev, newEdge]);
  }

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
