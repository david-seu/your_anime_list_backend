/* eslint-disable import/no-named-as-default */
import { useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { useNavigate, useParams } from 'react-router-dom'
import LinkButton from '../components/LinkButton'
import EditEpisodeForm from '../components/EditEpisodeForm'
import useUserStore from '../store/useUserStore'

export default function EditEpisode(): JSX.Element {
  const id = useParams<{ id: string }>().id as string

  const user = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  useEffect(() => {
    if (!user) {
      navigate('/')
    }
  }, [user, navigate])

  return (
    <div>
      <LinkButton to="/home">Back</LinkButton>
      <div className="edit--container">
        <EditEpisodeForm id={id} />
      </div>
    </div>
  )
}
