import { Form, Button } from 'react-bootstrap'
import { AlertColor } from '@mui/material'
import { useState } from 'react'
import CustomizedSnackbars from './CustomizedSnackBars'
import useConfirmLogin from '../hooks/userConfirmLogin'

export default function ConfirmLoginForm() {
  const [code, setCode] = useState(0)
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleSubmit = useConfirmLogin({
    code,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <div>
      <Form className="gap-2">
        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>6-digit Code</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter 6-digit code"
            required
            onChange={(e) => setCode(Number(e.target.value))}
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
