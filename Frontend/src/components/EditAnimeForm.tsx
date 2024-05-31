import { Button, Form } from 'react-bootstrap'
import { useState } from 'react'
import { AlertColor } from '@mui/material'
import useFetchAnimeById from '../hooks/useFetchAnimeById'
import useEditAnime from '../hooks/useEditAnime'
import CustomizedSnackbars from './CustomizedSnackBars'
import User from '../data/User'

interface EditAnimeFormProps {
  id: string
}

export default function EditAnimeForm({ id }: EditAnimeFormProps) {
  const [title, setTitle] = useState<string>('')
  const [score, setScore] = useState<number>(0)
  const [watched, setWatched] = useState<boolean>(false)
  const [numEpisodes, setNumEpisodes] = useState<number>(0)
  const [user, setUser] = useState<User>({} as User)

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  useFetchAnimeById({
    id,
    setTitle,
    setScore,
    setWatched,
    setNumEpisodes,
    setUser,
  })

  const handleSubmit = useEditAnime({
    user,
    id,
    title,
    score,
    watched,
    numEpisodes,
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
