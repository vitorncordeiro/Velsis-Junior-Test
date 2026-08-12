export interface User {
  id: number;
  username: string;
  email: string;
}

export interface UsersPage {
  content: User[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
}