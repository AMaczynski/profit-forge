# Graph Visualizer ↔ Party Backend Integration Plan

## How they map

| Graph concept | Party backend concept |
|---|---|
| Node | Party (`SeniorManager`, `RegionalManager`, `InsuranceAdvisor`) |
| Node `data.name` | `EmployeeResponse.name` |
| Node `data.attributes` | `{ email, type }` from `EmployeeResponse` |
| Edge | `PartyRelationship` |
| Edge source/target | `fromPartyId` / `toPartyId` |
| Edge `data.name` + `label` | `relationshipName` |
| *(no equivalent)* | `fromRole` / `toRole` |

---

## Gaps to close

### Backend — 3 things missing

**1. `GET /api/parties` — list all parties**
Currently there's only `GET /api/parties/{id}`. The frontend needs to load the full graph on
startup. Requires adding a new endpoint + `findAll()` on the repository.

**2. `GET /api/relationships` — list all relationships**
Currently only `GET /api/relationships?fromPartyId=...`. The frontend needs all relationships,
not just from one party.

**3. CORS configuration**
The frontend (Vite, port 5173) and backend (Spring Boot, port 8080) are on different origins.
In dev, a Vite proxy handles this; in production, Spring needs a `CorsConfigurationSource` bean.

---

### Frontend — 5 things to change

**4. API service layer (`src/api/partyApi.ts`)**
Typed `fetch` wrappers for each backend endpoint. No new library needed — native `fetch` is sufficient.

**5. Replace static data loading**
`GraphPage.tsx` currently bootstraps from `sampleData.ts`. Replace with a `useEffect` on mount
that calls the API, handles loading/error states, and maps backend responses to
`Node<NodeData>` / `Edge<EdgeData>`.

**6. Update `AddNodeForm`**
The current form takes a generic name + key-value attributes. It needs:
- A **party type dropdown** (SeniorManager / RegionalManager / InsuranceAdvisor)
- An **email field** instead of arbitrary attributes
- POSTs to the correct typed endpoint on submit

**7. Update `AddEdgeForm`**
Needs two extra fields — **fromRole** and **toRole** — before it can call `POST /api/relationships`.

**8. Node positions**
The backend has no concept of x/y coordinates — those are purely visual. Strategy:
- Auto-layout on first load
- `localStorage` to persist positions keyed by party ID so layout survives refresh

---

## Execution order

```
1. Backend: GET /api/parties
2. Backend: GET /api/relationships
3. Backend: CORS config
4. Frontend: Vite proxy (dev) → /api → localhost:8080
5. Frontend: src/api/partyApi.ts
6. Frontend: update types.ts
7. Frontend: GraphPage data loading (useEffect + loading state)
8. Frontend: AddNodeForm redesign (type + email)
9. Frontend: AddEdgeForm redesign (+ fromRole, toRole)
10. Frontend: node position strategy (auto-layout + localStorage)
```

Steps 1–3 are independent of 4–10 so backend and frontend work can proceed in parallel.
Steps 4–7 are a prerequisite for 8–10.
