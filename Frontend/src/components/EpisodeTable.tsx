import { Table } from 'react-bootstrap'
import { AlertColor } from '@mui/material'
import InfiniteScroll from 'react-infinite-scroll-component'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Episode from '../data/Episode'
import useEpisodeStore from '../store/useEpisodeStore'
import CustomizedSnackbars from './CustomizedSnackBars'
import Loader from './Loader'
import useFetchMoreEpisodes from '../hooks/useFetchMoreEpisodes'
import useFetchEpisodes from '../hooks/useFetchEpisodes'
import useUserStore from '../store/useUserStore'

export default function EpisodeTable() {
  const updateEpisodeStore = useEpisodeStore((state) => state.updateEpisode)
  const episodeList = useEpisodeStore((state) => state.getAllEpisodes)()
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const hasMore = useEpisodeStore((state) => state.hasMore)
  const user = useUserStore((state) => state.currentUser)
  const navigate = useNavigate()
  const setPageStoreEpisode = useEpisodeStore((state) => state.setPage)
  const setHasMoreStore = useEpisodeStore((state) => state.setHasMore)
  const setTitleStore = useEpisodeStore((state) => state.setTitle)
  const title = useEpisodeStore((state) => state.title)
  const setEpisodesListStore = useEpisodeStore((state) => state.setEpisodeList)
  const sort = useEpisodeStore((state) => state.sort)
  const setSortStore = useEpisodeStore((state) => state.setSort)

  const fetchEpisodes = useFetchEpisodes({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  useEffect(() => {
    setEpisodesListStore([])
    setTitleStore('')
    setSortStore('DESC')
    setPageStoreEpisode(0)
    setHasMoreStore(true)
    fetchEpisodes()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    setPageStoreEpisode(0)
    setHasMoreStore(true)
    fetchEpisodes()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [title, sort])

  const fetchMoreData = useFetchMoreEpisodes({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleDoubleClick = (episode: Episode) => {
    if (user?.role === 'ROLE_ADMIN') {
      navigate(`/viewEpisode/${episode.id}`)
    } else if (
      user?.role === 'ROLE_MANAGER' &&
      episode.anime!.user?.id === user.id
    ) {
      navigate(`/viewEpisode/${episode.id}`)
    }
  }

  return (
    <div>
      <InfiniteScroll
        dataLength={episodeList.length}
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
              <th>Number</th>
              <th>Season</th>
              <th>Watched</th>
              <th>Score</th>
              <th>Anime</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {episodeList.map((episode: Episode, index: number) => {
              return (
                <tr
                  key={episode.id}
                  onDoubleClick={() => handleDoubleClick(episode)}
                >
                  <td>{index + 1}</td>
                  <td>{episode.title}</td>
                  <td>{episode.number}</td>
                  <td>{episode.season}</td>
                  <td>{episode.watched ? 'Yes' : 'No'}</td>
                  <td>{episode.score > 0 ? episode.score : 'N/A'}</td>
                  <td>{episode.animeTitle}</td>
                  <td key={episode.id} className="button-container">
                    <input
                      type="checkbox"
                      onChange={(e) => {
                        // eslint-disable-next-line no-param-reassign
                        episode.checked = e.target.checked
                        updateEpisodeStore(episode)
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
