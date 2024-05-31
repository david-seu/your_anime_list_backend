/* eslint-disable import/no-named-as-default */
import { useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { useNavigate } from 'react-router-dom'
import SwapVertOutlinedIcon from '@mui/icons-material/SwapVertOutlined'
import AnimeTable from '../components/AnimeTable'
import EpisodeTable from '../components/EpisodeTable'
import CustomNavBar from '../components/Navbar'
import useUserStore from '../store/useUserStore'
import UserTable from '../components/UserTable'
import HandlerButton from '../components/HandlerButton'
import useAnimeStore from '../store/useAnimeStore'
import useEpisodeStore from '../store/useEpisodeStore'

export default function Home(): JSX.Element {
  const user = useUserStore((state) => state.currentUser)!
  const navigate = useNavigate()

  const setSortStoreAnime = useAnimeStore((state) => state.setSort)
  const setSortStoreEpisode = useEpisodeStore((state) => state.setSort)
  const setSortStoreUser = useUserStore((state) => state.setSort)
  const sortAnime = useAnimeStore((state) => state.sort)
  const sortEpisode = useEpisodeStore((state) => state.sort)
  const sortUser = useUserStore((state) => state.sort)

  useEffect(() => {
    if (!user) {
      navigate('/')
    }
  }, [user, navigate])

  const handleSortEpisode = () => {
    if (sortEpisode === 'ASC') {
      setSortStoreEpisode('DESC')
    } else {
      setSortStoreEpisode('ASC')
    }
  }

  const handleSortAnime = () => {
    if (sortAnime === 'ASC') {
      setSortStoreAnime('DESC')
    } else {
      setSortStoreAnime('ASC')
    }
  }

  const handleSortUser = () => {
    if (sortUser === 'ASC') {
      setSortStoreUser('DESC')
    } else {
      setSortStoreUser('ASC')
    }
  }

  return (
    <div>
      <CustomNavBar />
      <br />
      <div className="home--container">
        <h1>Your Anime List</h1>
        <br />
        <div className="tables--container">
          <div className="table">
            <HandlerButton onClick={handleSortAnime}>
              <SwapVertOutlinedIcon />
              Sort
            </HandlerButton>
            <AnimeTable />
          </div>
          <div className="table">
            <HandlerButton onClick={handleSortEpisode}>
              <SwapVertOutlinedIcon />
              Sort
            </HandlerButton>
            <EpisodeTable />
          </div>
          <div className="table">
            {user?.role === 'ROLE_ADMIN' && (
              <HandlerButton onClick={handleSortUser}>
                <SwapVertOutlinedIcon />
                Sort
              </HandlerButton>
            )}
            {user?.role === 'ROLE_ADMIN' && <UserTable />}
          </div>
        </div>
        <br />
      </div>
    </div>
  )
}
