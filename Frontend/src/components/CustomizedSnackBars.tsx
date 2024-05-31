import * as React from 'react'
import Snackbar from '@mui/material/Snackbar'
import Alert, { AlertColor } from '@mui/material/Alert'

interface CustomizedSnackbarsProps {
  open: boolean
  message: string
  type: AlertColor
  handleClose: (event?: React.SyntheticEvent | Event, reason?: string) => void
}

export default function CustomizedSnackbars({
  open,
  message,
  type,
  handleClose,
}: CustomizedSnackbarsProps): JSX.Element {
  return (
    <div>
      <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert
          onClose={handleClose}
          severity={type}
          variant="filled"
          sx={{ width: '100%' }}
        >
          {message}
        </Alert>
      </Snackbar>
    </div>
  )
}
