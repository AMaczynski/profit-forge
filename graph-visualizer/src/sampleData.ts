import type { Node, Edge } from '@xyflow/react';
import type { NodeData, EdgeData } from './types';

export const initialNodes: Node<NodeData>[] = [
  {
    id: '1',
    position: { x: 300, y: 50 },
    data: {
      name: 'Web Server',
      attributes: {
        host: 'nginx-01.prod',
        ip: '10.0.0.1',
        status: 'running',
        version: 'nginx/1.25',
      },
    },
    type: 'custom',
  },
  {
    id: '2',
    position: { x: 100, y: 220 },
    data: {
      name: 'Auth Service',
      attributes: {
        host: 'auth-01.prod',
        ip: '10.0.0.2',
        language: 'Go',
        port: '8081',
      },
    },
    type: 'custom',
  },
  {
    id: '3',
    position: { x: 500, y: 220 },
    data: {
      name: 'Product API',
      attributes: {
        host: 'api-01.prod',
        ip: '10.0.0.3',
        language: 'Node.js',
        port: '3000',
      },
    },
    type: 'custom',
  },
  {
    id: '4',
    position: { x: 100, y: 420 },
    data: {
      name: 'User DB',
      attributes: {
        host: 'pg-01.prod',
        ip: '10.0.0.4',
        engine: 'PostgreSQL 16',
        storage: '500 GB',
      },
    },
    type: 'custom',
  },
  {
    id: '5',
    position: { x: 500, y: 420 },
    data: {
      name: 'Product DB',
      attributes: {
        host: 'pg-02.prod',
        ip: '10.0.0.5',
        engine: 'PostgreSQL 16',
        storage: '1 TB',
      },
    },
    type: 'custom',
  },
  {
    id: '6',
    position: { x: 300, y: 420 },
    data: {
      name: 'Cache',
      attributes: {
        host: 'redis-01.prod',
        ip: '10.0.0.6',
        engine: 'Redis 7',
        memory: '8 GB',
      },
    },
    type: 'custom',
  },
];

export const initialEdges: Edge<EdgeData>[] = [
  {
    id: 'e1-2',
    source: '1',
    target: '2',
    data: { name: 'authenticates' },
    type: 'custom',
    label: 'authenticates',
  },
  {
    id: 'e1-3',
    source: '1',
    target: '3',
    data: { name: 'routes to' },
    type: 'custom',
    label: 'routes to',
  },
  {
    id: 'e2-4',
    source: '2',
    target: '4',
    data: { name: 'reads/writes' },
    type: 'custom',
    label: 'reads/writes',
  },
  {
    id: 'e3-5',
    source: '3',
    target: '5',
    data: { name: 'reads/writes' },
    type: 'custom',
    label: 'reads/writes',
  },
  {
    id: 'e3-6',
    source: '3',
    target: '6',
    data: { name: 'caches via' },
    type: 'custom',
    label: 'caches via',
  },
  {
    id: 'e2-6',
    source: '2',
    target: '6',
    data: { name: 'session cache' },
    type: 'custom',
    label: 'session cache',
  },
];
