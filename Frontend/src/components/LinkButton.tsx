import { Link } from 'react-router-dom'

interface LinkButtonProps {
  to: string
  children: React.ReactNode
}

export default function LinkButton({ to, children }: LinkButtonProps) {
  return (
    <Link to={to}>
      <button type="button" className="btn btn-primary btn-lg btn-add">
        {children}
      </button>
    </Link>
  )
}
