import React from 'react'
import ReactDOM from 'react-dom/client'
// eslint-disable-next-line import/extensions
import App from './App.tsx'
import './index.css'
// eslint-disable-next-line import/no-extraneous-dependencies

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
