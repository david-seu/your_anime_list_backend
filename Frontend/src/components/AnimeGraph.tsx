import { LineChart } from '@mui/x-charts/LineChart'
import { AlertColor } from '@mui/material'
import { useState } from 'react'
import CustomizedSnackbars from './CustomizedSnackBars'
import useFetchScoresCount from '../hooks/useFetchScoresCount'

export default function AnimeGraph() {
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarType, setSnackbarType] = useState('')
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [scoresCount, setScoresCount] = useState<number[]>([])

  useFetchScoresCount({
    setScoresCount,
    setSnackbarType,
    setSnackbarMessage,
    setSnackbarOpen,
  })()

  return (
    <div>
      <h2>Anime Scores</h2>
      <LineChart
        xAxis={[{ data: [-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10] }]}
        series={[
          {
            data: scoresCount,
          },
        ]}
        width={500}
        height={300}
      />
      <CustomizedSnackbars
        open={snackbarOpen}
        type={snackbarType as AlertColor}
        message={snackbarMessage}
        handleClose={() => setSnackbarOpen(false)}
      />
    </div>
  )
}
