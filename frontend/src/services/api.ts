import type { User, UsersPage} from '../types/index';

const API_URL = 'http://localhost:8080';


export class ApiError extends Error {
  status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
  }
}

export const api = {
  register: async (data: { username: string; password: string; email: string }) => {
    const res = await fetch(`${API_URL}/users`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!res.ok) throw new ApiError(await res.text(), res.status);
    return res.json();
  },

  getUsers: async (page = 0, size = 10, username?: string): Promise<UsersPage> => {
    const params = new URLSearchParams({ page: page.toString(), size: size.toString() });
    if (username) params.append('username', username);
    const res = await fetch(`${API_URL}/users?${params}`);
    if (!res.ok) throw new ApiError('Failed to fetch users', res.status);
    const data = await res.json();
    return {
      content: data.content,
      totalElements: data.totalElements,
      totalPages: data.totalPages,
      currentPage: page,
    };
  },

  getUserById: async (id: number): Promise<User> => {
    const res = await fetch(`${API_URL}/users/${id}`);
    if (res.status === 404) throw new ApiError('Not found', 404);
    if (!res.ok) throw new ApiError('Failed to fetch user', res.status);
    return res.json();
  },

  login: async (username: string, password: string) => {
    const res = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password }),
    });
    if (!res.ok) throw new ApiError('Invalid credentials', res.status);
    return res.json();
  },
};
