import User from './User'

interface Anime {
  id: number
  title: string
  watched: boolean
  score: number
  numEpisodes: number
  checked: boolean
  user: User | null
}

export default Anime
