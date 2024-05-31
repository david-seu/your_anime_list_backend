import * as React from 'react'
import Button from '@mui/material/Button'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
// eslint-disable-next-line import/no-extraneous-dependencies
import AddIcon from '@mui/icons-material/Add'
import { useNavigate } from 'react-router-dom'
import { ListItemIcon, ListItemText } from '@mui/material'
import useUserStore from '../store/useUserStore'

export default function AddMenu() {
  const currentUser = useUserStore((state) => state.currentUser)
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null)
  const open = Boolean(anchorEl)
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget)
  }
  const navigate = useNavigate()

  const handleAnime = () => {
    setAnchorEl(null)
    navigate('/addAnime')
  }

  const handleEpisode = () => {
    setAnchorEl(null)
    navigate('/addEpisode')
  }

  const handleUser = () => {
    setAnchorEl(null)
    navigate('/addUser')
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
          <AddIcon />
        </ListItemIcon>
        <ListItemText>Add</ListItemText>
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
        )}{' '}
      </Menu>
    </div>
  )
}
