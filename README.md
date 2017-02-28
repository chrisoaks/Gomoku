# Gomoku

Gomoku is like tic tac toe except it's five in a row instead of three.  And it's played on a much larger board.  (And in some versions, including this one, six in a row is prohibited).  I've put together a completely functioning human vs human playable game, and I'm working on version that allows for human vs computer.  The main idea is a combinatorial minimax search of the threatspace.  I hit a exponential explosion when searching more than 6 moves down, so I need to figure out how to intelligently trim the search space before I have a working AI.  Gomoku is a very interesting game because the possibilities are rich and the games are always novel.  And whereas Chess requires memorization of opening moves and extended subsequences, Gomoku is more related to fluid intelligence.