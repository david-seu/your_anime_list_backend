import 'bootstrap/dist/css/bootstrap.min.css'
import LinkButton from '../components/LinkButton'
import SignInForm from '../components/SignInForm'

export default function SignIn() {
  return (
    <div>
      <h1>SignIn</h1>
      <SignInForm />
      <LinkButton to="/register">Register</LinkButton>
    </div>
  )
}
