import { createJSONStorage, persist } from 'zustand/middleware'
import { create } from 'zustand'
import User from '../data/User'

interface UserStore {
  currentUser: User | null
  signIn: (user: User) => void
  signOut: () => void
  users: User[]
  page: number
  username: string
  sort: string
  setSort: (sort: string) => void
  setUsername: (username: string) => void
  addUser: (user: User) => void
  deleteUser: (id: number) => void
  updateUser: (user: User) => void
  getUsers: () => User[]
  getUser: (id: number) => User
  hasMore: boolean
  setHasMore: (hasMore: boolean) => void
  setUsers: (users: User[]) => void
  nextPage: () => void
  prevPage: () => void
  setPage: (page: number) => void
  appendUserList: (users: User[]) => void
}

export const useUserStore = create<UserStore>()(
  persist(
    (set, get) => ({
      currentUser: null,
      users: [],
      hasMore: true,
      page: 0,
      username: '',
      sort: 'DESC',
      setSort: (sort: string) => set({ sort }),
      setUsername: (username: string) => set({ username }),
      nextPage: () => set({ page: get().page + 1 }),
      prevPage: () => set({ page: get().page - 1 }),
      signIn: (user: User) => set({ currentUser: user }),
      signOut: () => set({ currentUser: null }),
      addUser: (user: User) => set({ users: [...get().users, user] }),
      deleteUser: (id: number) =>
        set({ users: get().users.filter((u) => u.id !== id) }),
      updateUser: (user: User) =>
        set({ users: get().users.map((u) => (u.id === user.id ? user : u)) }),
      getUsers: () => get().users,
      getUser: (id: number) => get().users.find((u) => u.id === id)!,
      setUsers: (users: User[]) => set({ users }),
      setHasMore: (hasMore: boolean) => set({ hasMore }),
      setPage: (page: number) => set({ page }),
      appendUserList: (users: User[]) =>
        set({ users: [...get().users, ...users] }),
    }),
    {
      name: 'user-store',
      storage: createJSONStorage(() => localStorage),
    }
  )
)

export default useUserStore
