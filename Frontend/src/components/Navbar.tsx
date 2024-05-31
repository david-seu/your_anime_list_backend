import AppBar from '@mui/material/AppBar'
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'
import HandlerButton from './HandlerButton'
import useSignOut from '../hooks/useSignOut'
import AddMenu from './AddMenu'
import DeleteMenu from './DeleteMenu'
import useUserStore from '../store/useUserStore'
import useHandleDownload from '../hooks/useHandleDownload'
import LinkButton from './LinkButton'
import User from '../data/User'
import FilterMenu from './FilterMenu'

export default function CustomNavBar() {
  const handleSignOut = useSignOut()
  const user = useUserStore((state) => state.currentUser) as User

  const handleDownload = useHandleDownload()

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed" color="transparent">
        <Toolbar sx={{ justifyContent: 'space-between' }}>
          <HandlerButton onClick={handleSignOut}>Sign Out</HandlerButton>
          {(user?.role === 'ROLE_ADMIN' || user?.role === 'ROLE_MANAGER') && (
            <AddMenu />
          )}
          {(user?.role === 'ROLE_ADMIN' || user?.role === 'ROLE_MANAGER') && (
            <DeleteMenu />
          )}
          <FilterMenu />
          <LinkButton to="/stats">Stats</LinkButton>
          <HandlerButton onClick={handleDownload}>Download Anime</HandlerButton>
        </Toolbar>
      </AppBar>
    </Box>
  )
}
