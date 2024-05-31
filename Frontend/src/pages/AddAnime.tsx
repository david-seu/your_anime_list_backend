import { useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { useNavigate } from 'react-router-dom'
import LinkButton from '../components/LinkButton'
import AddAnimeForm from '../components/AddAnimeForm'
import useUserStore from '../store/useUserStore'

export default function AddAnime() {
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
      <div className="add--container">
        <AddAnimeForm />
      </div>
    </div>
  )
}
