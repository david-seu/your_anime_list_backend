import { Table } from 'react-bootstrap'
import InfiniteScroll from 'react-infinite-scroll-component'
import { useEffect, useState } from 'react'
import { AlertColor } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import Anime from '../data/Anime'
import useAnimeStore from '../store/useAnimeStore'
import useFetchAnime from '../hooks/useFetchAnime'
import CustomizedSnackbars from './CustomizedSnackBars'
import Loader from './Loader'
import useUserStore from '../store/useUserStore'
import useFetchMoreAnime from '../hooks/useFetchMoreAnime'

export default function AnimeTable() {
  const animeList = useAnimeStore((state) => state.animeList)
  const updateAnimeStore = useAnimeStore((state) => state.updateAnime)
  const hasMore = useAnimeStore((state) => state.hasMore)
  const user = useUserStore((state) => state.currentUser)
  const navigate = useNavigate()

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const title = useAnimeStore((state) => state.title)
  const setTitleStore = useAnimeStore((state) => state.setTitle)
  const setPageStoreAnime = useAnimeStore((state) => state.setPage)
  const setHasMoreStore = useAnimeStore((state) => state.setHasMore)
  const setAnimeListStore = useAnimeStore((state) => state.setAnimeList)
  const sort = useAnimeStore((state) => state.sort)
  const setSortStore = useAnimeStore((state) => state.setSort)

  const fetchAnime = useFetchAnime({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  useEffect(() => {
    setAnimeListStore([])
    setTitleStore('')
    setSortStore('DESC')
    setPageStoreAnime(0)
    setHasMoreStore(true)
    fetchAnime()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    setPageStoreAnime(0)
    setHasMoreStore(true)
    fetchAnime()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [title, sort])

  const fetchMoreData = useFetchMoreAnime({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleDoubleClick = (anime: Anime) => {
    if (user?.role === 'ROLE_ADMIN') {
      navigate(`/viewAnime/${anime.id}`)
    } else if (user?.role === 'ROLE_MANAGER' && anime.user?.id === user.id) {
      navigate(`/viewAnime/${anime.id}`)
    }
  }

  return (
    <div>
      <InfiniteScroll
        dataLength={animeList.length}
        next={fetchMoreData}
        hasMore={hasMore}
        loader={<Loader />}
        endMessage={
          <p style={{ textAlign: 'center' }}>
            <b>Yay! You have seen it all</b>
          </p>
        }
        height={500}
      >
        <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Title</th>
              <th>Watched</th>
              <th>Score</th>
              <th>Number of Episodes</th>
              <th>User</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {animeList.map((anime: Anime, index: number) => {
              return (
                <tr
                  key={anime.id}
                  onDoubleClick={() => handleDoubleClick(anime)}
                >
                  <td>{index + 1}</td>
                  <td>{anime.title}</td>
                  <td>{anime.watched ? 'Yes' : 'No'}</td>
                  <td>{anime.score > 0 ? anime.score : 'N/A'}</td>
                  <td>{anime.numEpisodes}</td>
                  <td>{anime.user?.username}</td>
                  <td className="button-container">
                    <input
                      type="checkbox"
                      onChange={(e) => {
                        // eslint-disable-next-line no-param-reassign
                        anime.checked = e.target.checked
                        updateAnimeStore(anime)
                      }}
                    />
                  </td>
                </tr>
              )
            })}
          </tbody>
        </Table>
      </InfiniteScroll>
      <CustomizedSnackbars
        open={snackbarOpen}
        type={snackbarType as AlertColor}
        message={snackbarMessage}
        handleClose={() => setSnackbarOpen(false)}
      />
    </div>
  )
}
