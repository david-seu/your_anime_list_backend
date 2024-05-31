import { Form, Button } from 'react-bootstrap'
import { useState } from 'react'
import { AlertColor } from '@mui/material'
import useConfirmRegister from '../hooks/useConfirmRegister'
import CustomizedSnackbars from './CustomizedSnackBars'

export default function ConfirmRegisterForm() {
  const [token, setToken] = useState('')
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleSubmit = useConfirmRegister({
    token,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <div>
      <Form className="gap-2">
        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Token</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter confirmation token"
            required
            onChange={(e) => setToken(e.target.value)}
          />
        </Form.Group>
        <Button onClick={(e) => handleSubmit(e)} type="submit">
          Login
        </Button>
      </Form>
      <CustomizedSnackbars
        open={snackbarOpen}
        type={snackbarType as AlertColor}
        message={snackbarMessage}
        handleClose={() => setSnackbarOpen(false)}
      />
    </div>
  )
}
