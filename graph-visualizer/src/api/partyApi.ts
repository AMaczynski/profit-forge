export interface EmployeeResponse {
  id: string;
  type: string;
  name: string;
  email: string;
}

export interface PartyRelationshipResponse {
  id: string;
  fromPartyId: string;
  fromRole: string;
  toPartyId: string;
  toRole: string;
  relationshipName: string;
}

export interface CreateEmployeeRequest {
  name: string;
  email: string;
}

export interface AssignRelationshipRequest {
  fromPartyId: string;
  fromRole: string;
  toPartyId: string;
  toRole: string;
  relationshipName: string;
}

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`/api${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  if (!res.ok) {
    const message = await res.text().catch(() => res.statusText);
    throw new Error(message || res.statusText);
  }
  if (res.status === 204) return undefined as T;
  return res.json();
}

export const partyApi = {
  getAllParties: () =>
    request<EmployeeResponse[]>('/parties'),

  createSeniorManager: (data: CreateEmployeeRequest) =>
    request<EmployeeResponse>('/parties/senior-managers', { method: 'POST', body: JSON.stringify(data) }),

  createRegionalManager: (data: CreateEmployeeRequest) =>
    request<EmployeeResponse>('/parties/regional-managers', { method: 'POST', body: JSON.stringify(data) }),

  createInsuranceAdvisor: (data: CreateEmployeeRequest) =>
    request<EmployeeResponse>('/parties/insurance-advisors', { method: 'POST', body: JSON.stringify(data) }),

  getAllRelationships: () =>
    request<PartyRelationshipResponse[]>('/relationships'),

  assignRelationship: (data: AssignRelationshipRequest) =>
    request<PartyRelationshipResponse>('/relationships', { method: 'POST', body: JSON.stringify(data) }),

  deleteRelationship: (id: string) =>
    request<void>(`/relationships/${id}`, { method: 'DELETE' }),
};
