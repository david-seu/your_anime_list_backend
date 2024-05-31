import { Button, Form } from 'react-bootstrap'
import { AlertColor } from '@mui/material'
import { useState } from 'react'
import useFetchEpisodeById from '../hooks/useFetchEpisodeById'
import useEditEpisode from '../hooks/useEditEpisode'
import CustomizedSnackbars from './CustomizedSnackBars'

interface EditEpisodeFormProps {
  id: string
}

export default function EditEpisodeForm({ id }: EditEpisodeFormProps) {
  const [title, setTitle] = useState<string>('')
  const [number, setNumber] = useState<number>(0)
  const [season, setSeason] = useState<number>(0)
  const [score, setScore] = useState<number>(0)
  const [watched, setWatched] = useState<boolean>(false)
  const [animeTitle, setAnimeTitle] = useState<string>('')
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  useFetchEpisodeById({
    id,
    setTitle,
    setNumber,
    setSeason,
    setScore,
    setWatched,
    setAnimeTitle,
  })

  const handleSubmit = useEditEpisode({
    id,
    title,
    number,
    season,
    watched,
    score,
    animeTitle,
    setSnackbarOpen,
    setSnackbarType,
    setSnackbarMessage,
  })

  return (
    <div>
      <Form>
        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            value={title}
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setTitle(e.target.value)
            }
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formSore">
          <Form.Label className="">Number</Form.Label>
          <Form.Control
            type="number"
            value={number}
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setNumber(Number(e.target.value))
            }
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formSore">
          <Form.Label className="">Season</Form.Label>
          <Form.Control
            type="number"
            value={season}
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setSeason(Number(e.target.value))
            }
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formSore">
          <Form.Label className="">Score</Form.Label>
          <Form.Control
            type="number"
            value={score}
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setScore(Number(e.target.value))
            }
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formWatched">
          <Form.Check
            type="checkbox"
            label="Watched"
            checked={watched}
            required
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setWatched(e.target.checked)
            }
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
