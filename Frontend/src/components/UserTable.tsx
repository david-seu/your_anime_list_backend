import { Table } from 'react-bootstrap'
import InfiniteScroll from 'react-infinite-scroll-component'
import { useEffect, useState } from 'react'
import { AlertColor } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import CustomizedSnackbars from './CustomizedSnackBars'
import Loader from './Loader'
import useUserStore from '../store/useUserStore'
import useFetchUsers from '../hooks/useFetchUsers'
import User from '../data/User'
import useFetchMoreUsers from '../hooks/useFetchMoreUsers'

export default function UserTable() {
  const usersList = useUserStore((state) => state.getUsers)()
  const updateUserStore = useUserStore((state) => state.updateUser)
  const hasMore = useUserStore((state) => state.hasMore)

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const currentUser = useUserStore((state) => state.currentUser)
  const navigate = useNavigate()
  const setPageStoreUser = useUserStore((state) => state.setPage)
  const setHasMoreStore = useUserStore((state) => state.setHasMore)
  const username = useUserStore((state) => state.username)
  const setUsersListStore = useUserStore((state) => state.setUsers)
  const setUsernameStore = useUserStore((state) => state.setUsername)
  const setSortStore = useUserStore((state) => state.setSort)
  const sort = useUserStore((state) => state.sort)

  const fetchUsers = useFetchUsers({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  useEffect(() => {
    setUsersListStore([])
    setUsernameStore('')
    setPageStoreUser(0)
    setSortStore('DESC')
    setHasMoreStore(true)
    fetchUsers()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    setPageStoreUser(0)
    setHasMoreStore(true)
    fetchUsers()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [username, sort])

  const fetchMoreData = useFetchMoreUsers({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleDoubleClick = (user: User) => {
    if (currentUser?.role === 'ROLE_ADMIN') {
      navigate(`/viewUser/${user.id}`)
    }
  }

  return (
    <div>
      <InfiniteScroll
        dataLength={usersList.length}
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
              <th>Username</th>
              <th>Email</th>
              <th>Password</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            {usersList.map((user: User, index: number) => {
              return (
                <tr key={user.id} onDoubleClick={() => handleDoubleClick(user)}>
                  <td>{index + 1}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>{user.password}</td>
                  <td>{user.role}</td>
                  <td className="button-container">
                    <input
                      type="checkbox"
                      onChange={(e) => {
                        // eslint-disable-next-line no-param-reassign
                        user.checked = e.target.checked
                        updateUserStore(user)
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
