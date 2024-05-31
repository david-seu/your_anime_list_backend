import { useCallback } from 'react'
// eslint-disable-next-line import/no-named-as-default
import useAnimeStore from '../store/useAnimeStore'

const useHandleDownload = () => {
  const fileData = JSON.stringify(useAnimeStore((state) => state.getAllAnime)())
  return useCallback(() => {
    const blob = new Blob([fileData!], { type: 'text/plain' })
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = 'animeList.json'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    URL.revokeObjectURL(url)
  }, [fileData])
}

export default useHandleDownload
