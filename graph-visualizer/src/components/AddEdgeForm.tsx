import { useState } from 'react';
import type { Node } from '@xyflow/react';
import type { NodeData } from '../types';
import styles from './Forms.module.css';

interface Props {
  nodes: Node<NodeData>[];
  onAdd: (fromPartyId: string, fromRole: string, toPartyId: string, toRole: string, relationshipName: string) => void;
}

export default function AddEdgeForm({ nodes, onAdd }: Props) {
  const [fromPartyId, setFromPartyId] = useState('');
  const [fromRole, setFromRole] = useState('');
  const [toPartyId, setToPartyId] = useState('');
  const [toRole, setToRole] = useState('');
  const [relationshipName, setRelationshipName] = useState('');

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!fromPartyId || !fromRole.trim() || !toPartyId || !toRole.trim() || !relationshipName.trim()) return;
    if (fromPartyId === toPartyId) return;
    onAdd(fromPartyId, fromRole.trim(), toPartyId, toRole.trim(), relationshipName.trim());
    setFromPartyId('');
    setFromRole('');
    setToPartyId('');
    setToRole('');
    setRelationshipName('');
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h3 className={styles.formTitle}>Add Relationship</h3>
      <select className={styles.input} value={fromPartyId} onChange={e => setFromPartyId(e.target.value)} required>
        <option value="">From party…</option>
        {nodes.map(n => (
          <option key={n.id} value={n.id}>{n.data.name}</option>
        ))}
      </select>
      <input
        className={styles.input}
        placeholder="From role (e.g. Manager)"
        value={fromRole}
        onChange={e => setFromRole(e.target.value)}
        required
      />
      <select className={styles.input} value={toPartyId} onChange={e => setToPartyId(e.target.value)} required>
        <option value="">To party…</option>
        {nodes.map(n => (
          <option key={n.id} value={n.id}>{n.data.name}</option>
        ))}
      </select>
      <input
        className={styles.input}
        placeholder="To role (e.g. Advisor)"
        value={toRole}
        onChange={e => setToRole(e.target.value)}
        required
      />
      <input
        className={styles.input}
        placeholder="Relationship name (e.g. manages)"
        value={relationshipName}
        onChange={e => setRelationshipName(e.target.value)}
        required
      />
      <button className={styles.submitBtn} type="submit">Add Relationship</button>
    </form>
  );
}
