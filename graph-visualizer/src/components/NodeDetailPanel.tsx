import type { Node } from '@xyflow/react';
import type { NodeData } from '../types';
import styles from './NodeDetailPanel.module.css';

interface Props {
  node: Node<NodeData> | null;
  onClose: () => void;
}

export default function NodeDetailPanel({ node, onClose }: Props) {
  if (!node) return null;

  const { name, attributes } = node.data;

  return (
    <div className={styles.panel}>
      <div className={styles.header}>
        <h2 className={styles.title}>{name}</h2>
        <button className={styles.closeBtn} onClick={onClose} aria-label="Close">✕</button>
      </div>
      <div className={styles.section}>
        <h3 className={styles.sectionTitle}>Attributes</h3>
        {Object.keys(attributes).length === 0 ? (
          <p className={styles.empty}>No attributes</p>
        ) : (
          <table className={styles.table}>
            <tbody>
              {Object.entries(attributes).map(([key, value]) => (
                <tr key={key}>
                  <td className={styles.key}>{key}</td>
                  <td className={styles.value}>{value}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
      <div className={styles.section}>
        <p className={styles.meta}>Node ID: <code>{node.id}</code></p>
      </div>
    </div>
  );
}
