import { useState } from 'react';
import type { PartyType } from '../types';
import styles from './Forms.module.css';

const PARTY_TYPES: PartyType[] = ['SeniorManager', 'RegionalManager', 'InsuranceAdvisor'];

interface Props {
  onAdd: (partyType: PartyType, name: string, email: string) => void;
}

export default function AddNodeForm({ onAdd }: Props) {
  const [partyType, setPartyType] = useState<PartyType>('InsuranceAdvisor');
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!name.trim() || !email.trim()) return;
    onAdd(partyType, name.trim(), email.trim());
    setName('');
    setEmail('');
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h3 className={styles.formTitle}>Add Party</h3>
      <select
        className={styles.input}
        value={partyType}
        onChange={e => setPartyType(e.target.value as PartyType)}
      >
        {PARTY_TYPES.map(t => (
          <option key={t} value={t}>{t}</option>
        ))}
      </select>
      <input
        className={styles.input}
        placeholder="Name"
        value={name}
        onChange={e => setName(e.target.value)}
        required
      />
      <input
        className={styles.input}
        placeholder="Email"
        type="email"
        value={email}
        onChange={e => setEmail(e.target.value)}
        required
      />
      <button className={styles.submitBtn} type="submit">Add Party</button>
    </form>
  );
}
