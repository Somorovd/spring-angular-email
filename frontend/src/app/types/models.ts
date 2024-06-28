export interface Address {
  username: string;
  server: string;
}

export interface Email {
  id: number;
  chainId: number;
  isDraft: boolean;
  subject: string;
  body: string;
  date: Date;
  sender: Address;
  recipients: Address[];
}

export interface Status {
  id: number;
  address: Address;
  chainId: number;
  email: Email;
  isRead: boolean;
  isStarred: boolean;
  location: string;
}

export interface Errors extends Record<string, string> {}
