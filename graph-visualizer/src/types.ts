export type PartyType = 'SeniorManager' | 'RegionalManager' | 'InsuranceAdvisor';

export interface NodeData {
  name: string;
  email: string;
  partyType: PartyType;
  [key: string]: unknown;
}

export interface EdgeData {
  name: string;
  fromRole: string;
  toRole: string;
  [key: string]: unknown;
}
