# Graph Visualizer

An interactive graph visualization UI built with React, TypeScript, and React Flow.

## Features

- **Named nodes** with arbitrary key-value attributes
- **Named edges** with labels displayed on connection lines
- **Click a node** to open a detail panel showing all its attributes
- **Add nodes** via the sidebar form with dynamic attribute rows
- **Add edges** via the sidebar form with source, target, and connection name
- **Search nodes** by name — non-matching nodes dim in real time
- Pan, zoom, and drag nodes to reposition them

## Getting Started

```bash
npm install
npm run dev
```

Then open [http://localhost:5173](http://localhost:5173).

## Stack

- [React](https://react.dev/) + [TypeScript](https://www.typescriptlang.org/)
- [React Flow](https://reactflow.dev/) — graph rendering, pan/zoom, drag
- [Vite](https://vitejs.dev/) — build tool
