import Anime from './Anime'

interface Episode {
  id: number
  title: string
  number: number
  season: number
  score: number
  checked: boolean
  watched: boolean
  animeTitle: string
  anime: Anime | null
}

export default Episode
