/* eslint-disable import/no-named-as-default */
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import LinkButton from '../components/LinkButton'
import useFetchAnimeById from '../hooks/useFetchAnimeById'
import useUserStore from '../store/useUserStore'
import User from '../data/User'

export default function ViewAnime(): JSX.Element {
  const [title, setTitle] = useState<string>('')
  const [score, setScore] = useState<number>(0)
  const [watched, setWatched] = useState<boolean>(false)
  const [numEpisodes, setNumEpisodes] = useState<number>(0)
  const [user, setUser] = useState({} as User)

  const { id } = useParams<{ id: string }>() as unknown as { id: string }

  const currentUser = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  useEffect(() => {
    if (!currentUser) {
      navigate('/')
    }
  }, [currentUser, navigate])

  useFetchAnimeById({
    id,
    setTitle,
    setScore,
    setWatched,
    setNumEpisodes,
    setUser,
  })

  return (
    <div>
      <LinkButton to="/home">Back</LinkButton>
      <LinkButton to={`/editAnime/${id}`}>Edit</LinkButton>
      <div className="view--container">
        <h1>{title}</h1>
        <p>Watched: {watched ? 'Yes' : 'No'}</p>
        <p>Score: {score < 0 ? 'N/A' : score}</p>
        <p>Number of Episodes: {numEpisodes}</p>
        <p>User: {user.username}</p>
      </div>
    </div>
  )
}
