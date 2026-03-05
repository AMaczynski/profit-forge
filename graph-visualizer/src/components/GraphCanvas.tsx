import { useCallback } from 'react';
import {
  ReactFlow,
  Controls,
  Background,
  BackgroundVariant,
  type Node,
  type Edge,
  type NodeChange,
  type EdgeChange,
  type OnConnect,
  applyNodeChanges,
  applyEdgeChanges,
} from '@xyflow/react';
import type { NodeData, EdgeData } from '../types';
import CustomNode from './CustomNode';

const nodeTypes = { custom: CustomNode };

interface Props {
  nodes: Node<NodeData>[];
  edges: Edge<EdgeData>[];
  onNodesChange: (changes: NodeChange[]) => void;
  onEdgesChange: (changes: EdgeChange[]) => void;
  onConnect: OnConnect;
  onNodeClick: (node: Node<NodeData>) => void;
}

export { applyNodeChanges, applyEdgeChanges };

export default function GraphCanvas({ nodes, edges, onNodesChange, onEdgesChange, onConnect, onNodeClick }: Props) {
  const handleNodeClick = useCallback(
    (_: React.MouseEvent, node: Node) => {
      onNodeClick(node as Node<NodeData>);
    },
    [onNodeClick]
  );

  return (
    <ReactFlow
      nodes={nodes}
      edges={edges}
      nodeTypes={nodeTypes}
      onNodesChange={onNodesChange}
      onEdgesChange={onEdgesChange}
      onConnect={onConnect}
      onNodeClick={handleNodeClick}
      fitView
      defaultEdgeOptions={{ animated: false }}
    >
      <Controls />
      <Background variant={BackgroundVariant.Dots} gap={20} size={1} color="#d1d5db" />
    </ReactFlow>
  );
}
