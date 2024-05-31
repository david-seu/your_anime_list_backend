import { useState } from 'react'
import { Form, Button } from 'react-bootstrap'
import { AlertColor } from '@mui/material'
import CustomizedSnackbars from './CustomizedSnackBars'
import useAddAnime from '../hooks/useAddAnime'

export default function AddAnimeForm() {
  const [title, setTitle] = useState('')
  const [score, setScore] = useState<number>(0)
  const [watched, setWatched] = useState(false)
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleSubmit = useAddAnime({
    title,
    score,
    watched,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <div>
      <Form noValidate className="gap-2">
        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter title"
            required
            onChange={(e) => setTitle(e.target.value)}
          />
          <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formScore">
          <Form.Label>Score</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter score"
            required
            onChange={(e) => setScore(Number(e.target.value))}
          />
          <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formWatched">
          <Form.Check
            type="checkbox"
            label="Watched"
            required
            onChange={(e) => setWatched(e.target.checked)}
          />
          <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
        </Form.Group>
        <Button onClick={handleSubmit} type="submit">
          Submit
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
