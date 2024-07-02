import { InboxStateInterface } from './inboxState.interface';

export interface MailStateInterface {
  inbox: InboxStateInterface;
  isLoading: boolean;
  error: string | null;
}
