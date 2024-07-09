import { EmailDetailStateInterface } from './emailDetailsState.interface';
import { InboxStateInterface } from './inboxState.interface';

export interface MailStateInterface {
  inbox: InboxStateInterface;
  details: EmailDetailStateInterface;
  isLoading: boolean;
  error: string | null;
}
