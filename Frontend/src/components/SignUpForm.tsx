import { Form, Button } from 'react-bootstrap'
import { AlertColor } from '@mui/material'
import { useState } from 'react'
import CustomizedSnackbars from './CustomizedSnackBars'
import useSignUp from '../hooks/useSignUp'

export default function SignUpForm() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [email, setEmail] = useState('')
  const [role, setRole] = useState('')
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleSubmit = useSignUp({
    username,
    password,
    email,
    role,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <Form className="gap-2">
      <Form.Group className="mb-3" controlId="formName">
        <Form.Label>Username</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter username"
          required
          onChange={(e) => setUsername(e.target.value)}
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formRole">
        <Form.Label>Role</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter role"
          required
          onChange={(e) => setRole(e.target.value)}
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formEmail">
        <Form.Label>Email</Form.Label>
        <Form.Control
          type="email"
          placeholder="Enter email"
          required
          onChange={(e) => setEmail(e.target.value)}
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formPassword">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="password"
          placeholder="Enter password"
          required
          onChange={(e) => setPassword(e.target.value)}
        />
      </Form.Group>

      <Button onClick={(e) => handleSubmit(e)} type="submit">
        Submit
      </Button>
      <CustomizedSnackbars
        open={snackbarOpen}
        type={snackbarType as AlertColor}
        message={snackbarMessage}
        handleClose={() => setSnackbarOpen(false)}
      />
    </Form>
  )
}
