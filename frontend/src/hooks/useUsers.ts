import { useState, useCallback } from 'react';
import { api } from '../services/api';
import type { UsersPage } from '../types/index';

export function useUsers() {
  const [data, setData] = useState<UsersPage | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetch = useCallback(async (page = 0, username?: string) => {
    setLoading(true);
    setError(null);
    try {
      const result = await api.getUsers(page, 10, username);
      setData(result);
      return result;
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Failed to fetch users';
      setError(msg);
    } finally {
      setLoading(false);
    }
  }, []);

  const fetchById = useCallback(async (id: number) => {
    setLoading(true);
    setError(null);
    try {
      const user = await api.getUserById(id);
      setData({ content: [user], totalElements: 1, totalPages: 1, currentPage: 0 });
      return user;
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'User not found';
      setError(msg);
    } finally {
      setLoading(false);
    }
  }, []);

  return { data, loading, error, fetch, fetchById };
}
