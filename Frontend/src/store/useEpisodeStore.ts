import { createJSONStorage, persist } from 'zustand/middleware'
import { create } from 'zustand'
import Episode from '../data/Episode'

interface EpisodeStore {
  episodeList: Episode[]
  page: number
  hasMore: boolean
  title: string
  sort: string
  setSort: (sort: string) => void
  setTitle: (title: string) => void
  setHasMore: (hasMore: boolean) => void
  addEpisode: (episode: Episode) => void
  deleteEpisode: (id: number) => void
  updateEpisode: (episode: Episode) => void
  setEpisodeList: (episodeList: Episode[]) => void
  getEpisode: (id: number) => Episode
  getAllEpisodes: () => Episode[]
  nextPage: () => void
  prevPage: () => void
  setPage: (page: number) => void
  appendEpisodeList: (episodeList: Episode[]) => void
}

export const useEpisodeStore = create<EpisodeStore>()(
  persist(
    (set, get) => ({
      episodeList: [],
      page: 0,
      hasMore: true,
      title: '',
      sort: 'DESC',
      setSort: (sort: string) => set({ sort }),
      setTitle: (title: string) => set({ title }),
      nextPage: () => set({ page: get().page + 1 }),
      prevPage: () => set({ page: get().page - 1 }),
      setHasMore: (hasMore: boolean) => set({ hasMore }),
      setEpisodeList: (episodeList: Episode[]) => set({ episodeList }),
      addEpisode: (episode: Episode) =>
        set({ episodeList: [...get().episodeList, episode] }),
      deleteEpisode: (id: number) =>
        set({
          episodeList: get().episodeList.filter((episode) => episode.id !== id),
        }),
      updateEpisode: (episode: Episode) =>
        set({
          episodeList: get().episodeList.map((e) =>
            e.id === episode.id ? episode : e
          ),
        }),
      getEpisode: (id: number) =>
        get().episodeList.find((episode) => episode.id === id)!,
      getAllEpisodes: () => get().episodeList,
      setPage: (page: number) => set({ page }),
      appendEpisodeList: (episodeList: Episode[]) =>
        set({ episodeList: [...get().episodeList, ...episodeList] }),
    }),
    {
      name: 'episode-store',
      storage: createJSONStorage(() => localStorage),
    }
  )
)

export default useEpisodeStore
