export interface NodeData {
  name: string;
  attributes: Record<string, string>;
  [key: string]: unknown;
}

export interface EdgeData {
  name: string;
  [key: string]: unknown;
}
