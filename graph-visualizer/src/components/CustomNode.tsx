import { memo } from 'react';
import { Handle, Position } from '@xyflow/react';
import type { NodeProps } from '@xyflow/react';
import type { NodeData } from '../types';
import styles from './CustomNode.module.css';

function CustomNode({ data, selected }: NodeProps) {
  const nodeData = data as NodeData;
  return (
    <div className={`${styles.node} ${selected ? styles.selected : ''}`}>
      <Handle type="target" position={Position.Top} />
      <div className={styles.name}>{nodeData.name}</div>
      <Handle type="source" position={Position.Bottom} />
    </div>
  );
}

export default memo(CustomNode);
