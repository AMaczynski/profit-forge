import { useState } from 'react';
import type { Node } from '@xyflow/react';
import type { NodeData } from '../types';
import styles from './Forms.module.css';

interface Props {
  nodes: Node<NodeData>[];
  onAdd: (source: string, target: string, name: string) => void;
}

export default function AddEdgeForm({ nodes, onAdd }: Props) {
  const [source, setSource] = useState('');
  const [target, setTarget] = useState('');
  const [name, setName] = useState('');

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!source || !target || !name.trim()) return;
    if (source === target) return;
    onAdd(source, target, name.trim());
    setSource('');
    setTarget('');
    setName('');
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h3 className={styles.formTitle}>Add Edge</h3>
      <select className={styles.input} value={source} onChange={e => setSource(e.target.value)} required>
        <option value="">From node…</option>
        {nodes.map(n => (
          <option key={n.id} value={n.id}>{n.data.name}</option>
        ))}
      </select>
      <select className={styles.input} value={target} onChange={e => setTarget(e.target.value)} required>
        <option value="">To node…</option>
        {nodes.map(n => (
          <option key={n.id} value={n.id}>{n.data.name}</option>
        ))}
      </select>
      <input
        className={styles.input}
        placeholder="Connection name"
        value={name}
        onChange={e => setName(e.target.value)}
        required
      />
      <button className={styles.submitBtn} type="submit">Add Edge</button>
    </form>
  );
}
