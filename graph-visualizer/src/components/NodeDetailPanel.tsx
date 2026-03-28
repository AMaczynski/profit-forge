import type { Node } from '@xyflow/react';
import type { NodeData } from '../types';
import styles from './NodeDetailPanel.module.css';

interface Props {
  node: Node<NodeData> | null;
  onClose: () => void;
}

export default function NodeDetailPanel({ node, onClose }: Props) {
  if (!node) return null;

  const { name, email, partyType } = node.data;

  return (
    <div className={styles.panel}>
      <div className={styles.header}>
        <h2 className={styles.title}>{name}</h2>
        <button className={styles.closeBtn} onClick={onClose} aria-label="Close">✕</button>
      </div>
      <div className={styles.section}>
        <h3 className={styles.sectionTitle}>Details</h3>
        <table className={styles.table}>
          <tbody>
            <tr>
              <td className={styles.key}>Type</td>
              <td className={styles.value}>{partyType}</td>
            </tr>
            <tr>
              <td className={styles.key}>Email</td>
              <td className={styles.value}>{email}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div className={styles.section}>
        <p className={styles.meta}>ID: <code>{node.id}</code></p>
      </div>
    </div>
  );
}
