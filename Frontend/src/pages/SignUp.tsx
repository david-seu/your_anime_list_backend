import 'bootstrap/dist/css/bootstrap.min.css'
import LinkButton from '../components/LinkButton'
import SignUpForm from '../components/SignUpForm'

export default function SingUp() {
  return (
    <div>
      <LinkButton to="/">Back</LinkButton>
      <SignUpForm />
    </div>
  )
}
