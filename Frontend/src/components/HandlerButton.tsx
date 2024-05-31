interface HandlerButtonProps {
  onClick: () => void
  children: React.ReactNode
}

export default function HandlerButton({
  onClick,
  children,
}: HandlerButtonProps) {
  return (
    <button
      type="button"
      className="btn btn-primary btn-lg btn-add"
      onClick={onClick}
    >
      {children}
    </button>
  )
}
