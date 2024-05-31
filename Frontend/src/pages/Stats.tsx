import Graph from '../components/AnimeGraph'
import LinkButton from '../components/LinkButton'

export default function Stats() {
  return (
    <div>
      <LinkButton to="/home">Home</LinkButton>
      <h1>Stats</h1>
      <Graph />
    </div>
  )
}
