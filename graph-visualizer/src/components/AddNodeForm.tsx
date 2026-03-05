import { useState } from 'react';
import type { NodeData } from '../types';
import styles from './Forms.module.css';

interface Props {
  onAdd: (data: NodeData) => void;
}

export default function AddNodeForm({ onAdd }: Props) {
  const [name, setName] = useState('');
  const [attrs, setAttrs] = useState<{ key: string; value: string }[]>([{ key: '', value: '' }]);

  function handleAttrChange(index: number, field: 'key' | 'value', val: string) {
    setAttrs(prev => prev.map((a, i) => i === index ? { ...a, [field]: val } : a));
  }

  function addAttrRow() {
    setAttrs(prev => [...prev, { key: '', value: '' }]);
  }

  function removeAttrRow(index: number) {
    setAttrs(prev => prev.filter((_, i) => i !== index));
  }

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!name.trim()) return;

    const attributes: Record<string, string> = {};
    for (const { key, value } of attrs) {
      if (key.trim()) attributes[key.trim()] = value;
    }

    onAdd({ name: name.trim(), attributes });
    setName('');
    setAttrs([{ key: '', value: '' }]);
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h3 className={styles.formTitle}>Add Node</h3>
      <input
        className={styles.input}
        placeholder="Node name"
        value={name}
        onChange={e => setName(e.target.value)}
        required
      />
      <div className={styles.attrList}>
        {attrs.map((attr, i) => (
          <div key={i} className={styles.attrRow}>
            <input
              className={styles.inputSm}
              placeholder="key"
              value={attr.key}
              onChange={e => handleAttrChange(i, 'key', e.target.value)}
            />
            <input
              className={styles.inputSm}
              placeholder="value"
              value={attr.value}
              onChange={e => handleAttrChange(i, 'value', e.target.value)}
            />
            <button
              type="button"
              className={styles.removeBtn}
              onClick={() => removeAttrRow(i)}
              disabled={attrs.length === 1}
            >✕</button>
          </div>
        ))}
        <button type="button" className={styles.addAttrBtn} onClick={addAttrRow}>
          + attribute
        </button>
      </div>
      <button className={styles.submitBtn} type="submit">Add Node</button>
    </form>
  );
}
