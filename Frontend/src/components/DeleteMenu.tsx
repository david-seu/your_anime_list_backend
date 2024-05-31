import Button from '@mui/material/Button'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
// eslint-disable-next-line import/no-extraneous-dependencies
import RemoveIcon from '@mui/icons-material/Remove'
import { useState } from 'react'
import { AlertColor } from '@mui/material'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import CustomizedSnackbars from './CustomizedSnackBars'
import useHandleDeleteAnime from '../hooks/useHandleDeleteAnime'
import useHandleDeleteEpisode from '../hooks/useHandleDeleteEpisode'
import useHandleDeleteUser from '../hooks/useHandleDeleteUser'
import useUserStore from '../store/useUserStore'

export default function RemoveMenu() {
  const currentUser = useUserStore((state) => state.currentUser)

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const open = Boolean(anchorEl)
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')

  const handleDeleteAnime = useHandleDeleteAnime({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleDeleteEpisode = useHandleDeleteEpisode({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleDeleteUser = useHandleDeleteUser({
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })

  const handleAnime = () => {
    console.log('handleAnime')
    handleDeleteAnime()
    setAnchorEl(null)
  }

  const handleEpisode = () => {
    handleDeleteEpisode()
    setAnchorEl(null)
  }

  const handleUser = () => {
    handleDeleteUser()
    setAnchorEl(null)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
        variant="contained"
      >
        <ListItemIcon>
          <RemoveIcon />
        </ListItemIcon>
        <ListItemText>Delete</ListItemText>
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        <MenuItem onClick={handleAnime}>Anime</MenuItem>
        <MenuItem onClick={handleEpisode}>Episode</MenuItem>
        {currentUser?.role === 'ROLE_ADMIN' && (
          <MenuItem onClick={handleUser}>User</MenuItem>
        )}
      </Menu>
      <CustomizedSnackbars
        open={snackbarOpen}
        type={snackbarType as AlertColor}
        message={snackbarMessage}
        handleClose={() => setSnackbarOpen(false)}
      />
    </div>
  )
}
