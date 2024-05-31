import { Form, Button } from 'react-bootstrap'
import { useState } from 'react'
import { AlertColor } from '@mui/material'
import useAddEpisode from '../hooks/useAddEpisode'
import CustomizedSnackbars from './CustomizedSnackBars'

function AddEpisodeForm() {
  const [title, setTitle] = useState('')
  const [number, setNumber] = useState<number>(0)
  const [season, setSeason] = useState<number>(0)
  const [score, setScore] = useState<number>(0)
  const [watched, setWatched] = useState(false)
  const [animeTitle, setAnimeTitle] = useState('')
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleSubmit = useAddEpisode({
    title,
    number,
    season,
    score,
    watched,
    animeTitle,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <div>
      <Form className="gap-2">
        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter title"
            required
            onChange={(e) => setTitle(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formAge">
          <Form.Label>Number</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter episode number"
            required
            onChange={(e) => setNumber(Number(e.target.value))}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formAge">
          <Form.Label>Season</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter season number"
            required
            onChange={(e) => setSeason(Number(e.target.value))}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formAge">
          <Form.Label>Score</Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter score"
            required
            onChange={(e) => setScore(Number(e.target.value))}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formAge">
          <Form.Label>Anime</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter the title of the anime it belongs"
            required
            onChange={(e) => setAnimeTitle(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formWatched">
          <Form.Check
            type="checkbox"
            label="Watched"
            required
            onChange={(e) => setWatched(e.target.checked)}
          />
        </Form.Group>
        <Button onClick={(e) => handleSubmit(e)} type="submit">
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

export default AddEpisodeForm
