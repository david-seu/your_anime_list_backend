import { createJSONStorage, persist } from 'zustand/middleware'
import { create } from 'zustand'
import Anime from '../data/Anime'

interface AnimeStore {
  animeList: Anime[]
  page: number
  hasMore: boolean
  title: string
  sort: string
  setSort: (sort: string) => void
  addAnime: (anime: Anime) => void
  deleteAnime: (id: number) => void
  updateAnime: (anime: Anime) => void
  setAnimeList: (animeList: Anime[]) => void
  setHasMore: (hasMore: boolean) => void
  getAnime: (id: number) => Anime
  getAllAnime: () => Anime[]
  nextPage: () => void
  prevPage: () => void
  setPage: (page: number) => void
  appendAnimeList: (animeList: Anime[]) => void
  setTitle: (title: string) => void
}

export const useAnimeStore = create<AnimeStore>()(
  persist(
    (set, get) => ({
      animeList: [],
      page: 0,
      hasMore: true,
      title: '',
      sort: 'DESC',
      setSort: (sort: string) => set({ sort }),
      setTitle: (title: string) => set({ title }),
      nextPage: () => set({ page: get().page + 1 }),
      prevPage: () => set({ page: get().page - 1 }),
      setAnimeList: (animeList: Anime[]) => set({ animeList }),
      setHasMore: (hasMore: boolean) => set({ hasMore }),
      addAnime: (anime: Anime) =>
        set({ animeList: [...get().animeList, anime] }),
      deleteAnime: (id: number) =>
        set({ animeList: get().animeList.filter((anime) => anime.id !== id) }),
      updateAnime: (anime: Anime) =>
        set({
          animeList: get().animeList.map((a) =>
            a.id === anime.id ? anime : a
          ),
        }),
      getAnime: (id: number) =>
        get().animeList.find((anime) => anime.id === id)!,
      getAllAnime: () => get().animeList,
      setPage: (page: number) => set({ page }),
      appendAnimeList: (animeList: Anime[]) =>
        set({ animeList: [...get().animeList, ...animeList] }),
    }),
    {
      name: 'anime-store',
      storage: createJSONStorage(() => localStorage),
    }
  )
)

export default useAnimeStore
