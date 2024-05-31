import Button from '@mui/material/Button'
import Menu from '@mui/material/Menu'
import FilterAltIcon from '@mui/icons-material/FilterAlt'
import { useState } from 'react'
import { ListItem, TextField } from '@mui/material'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import useUserStore from '../store/useUserStore'
import useAnimeStore from '../store/useAnimeStore'
import useEpisodeStore from '../store/useEpisodeStore'

export default function AddMenu() {
  const currentUser = useUserStore((state) => state.currentUser)

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const open = Boolean(anchorEl)
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const [animeTitle, setAnimeTitle] = useState('' as string)
  const [episodeTitle, setEpisodeTitle] = useState('' as string)
  const [userUsername, setUserUsername] = useState('' as string)
  const setUsernameStore = useUserStore((state) => state.setUsername)
  const setAnimeTitleStore = useAnimeStore((state) => state.setTitle)
  const setEpisodeTitleStore = useEpisodeStore((state) => state.setTitle)

  const handleAnimeTitleChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setAnimeTitle(event.target.value)
  }

  const handleEpisodeTitleChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setEpisodeTitle(event.target.value)
  }

  const handleUserUsernameChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setUserUsername(event.target.value)
  }

  const handleSubmitAnime = (event: React.FormEvent) => {
    event.preventDefault()
    console.log('animeTitle', animeTitle)
    setAnimeTitleStore(animeTitle)
  }
  const handleSubmitEpisode = (event: React.FormEvent) => {
    event.preventDefault()
    console.log('episodeTitle', episodeTitle)
    setEpisodeTitleStore(episodeTitle)
  }

  const handleSubmitUser = (event: React.FormEvent) => {
    event.preventDefault()
    console.log('userUsername', userUsername)
    setUsernameStore(userUsername)
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
          <FilterAltIcon />
        </ListItemIcon>
        <ListItemText>Filter</ListItemText>
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
        <ListItem>
          <TextField label="Anime Title" onChange={handleAnimeTitleChange} />
          <Button onClick={handleSubmitAnime}>Submit</Button>
        </ListItem>
        <ListItem>
          <TextField
            label="Episode Title"
            onChange={handleEpisodeTitleChange}
          />
          <Button onClick={handleSubmitEpisode}>Submit</Button>
        </ListItem>
        {currentUser?.role === 'ROLE_ADMIN' && (
          <ListItem>
            <TextField
              label="User Username"
              onChange={handleUserUsernameChange}
            />
            <Button onClick={handleSubmitUser}>Submit</Button>
          </ListItem>
        )}
      </Menu>
    </div>
  )
}
