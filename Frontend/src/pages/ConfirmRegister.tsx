import ConfirmRegisterForm from '../components/ConfirmRegisterForm'
import LinkButton from '../components/LinkButton'

export default function ConfirmRegister() {
  return (
    <div>
      <LinkButton to="/">Back</LinkButton>
      <ConfirmRegisterForm />
    </div>
  )
}
