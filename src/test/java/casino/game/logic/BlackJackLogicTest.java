package casino.game.logic;

import casino.game.model.Position;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.model.game.elements.Card;
import casino.game.model.game.elements.CardDeck;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.bet.SimpleBet;
import casino.game.strategy.points.BlackJackPoints;
import casino.game.strategy.points.PointsStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BlackJackLogicTest {

    @Mock
    private Blackjack mockGame;
    @Mock
    private CardDeck mockDeck;
    @Mock
    private PointsStrategy mockPointsStrategy;
    @Mock
    private BetStrategy mockBetStrategy;

    private BlackJackLogic spyBlackJackLogic;

    private BlackJackLogic mockBlackJackLogic;
    private BlackJackLogic blackJackLogic;
    private Blackjack game;


    @BeforeEach
    void BaseSetup() {
        BlackJackPoints pointsStrategy = new BlackJackPoints();
        BetStrategy betStrategy = new SimpleBet();  //we will use this fot default;

        game = new Blackjack(betStrategy, pointsStrategy);

        game.setDeck(new CardDeck());

        MockitoAnnotations.openMocks(this);

        // three different blackJackLogics for different types of tests
        mockBlackJackLogic = new BlackJackLogic(mockGame, mockBetStrategy, mockPointsStrategy);
        spyBlackJackLogic = spy(new BlackJackLogic(mockGame, mockBetStrategy, mockPointsStrategy));
        blackJackLogic = new BlackJackLogic(game, betStrategy, pointsStrategy);


        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card("10","♠", 0, 0, true));
        dealerCards.add(new Card("4","♠", 0, 0, true));

        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card("9","♠", 0, 0, true));
        playerCards.add(new Card("5","♠", 0, 0, true));

        when(mockGame.getDealerCards()).thenReturn(dealerCards);
        when(mockGame.getPlayerCards()).thenReturn(playerCards);
        when(mockGame.getDeck()).thenReturn(mockDeck);
        when(mockGame.getDeck().getCard()).thenReturn(new Card("8","♠", 0, 0, true));
    }


    @Test
    void testInitializeGame() {   //don't pass
        doNothing().when(mockDeck).shuffle();
        when(mockGame.getDeck()).thenReturn(mockDeck);

        mockBlackJackLogic.initializeGame();

        verify(mockDeck).shuffle();
        verify(mockGame, times(2)).addPlayerCard(any(Card.class));
        verify(mockGame, times(2)).addDealerCard(any(Card.class));
        verify(mockGame).dealerFaceDown();
    }


    @Test
    void testDrawCardForDealer() {
        int initialDeckSize = game.getDeck().getDeckSize();
        blackJackLogic.drawCardForDealer();

        assertEquals(1, game.getDealerCards().size());

        assertEquals(initialDeckSize - 1, game.getDeck().getDeckSize());
    }


    @Test
    void testDrawCardForPlayer() {
        int initialDeckSize = game.getDeck().getDeckSize();
        blackJackLogic.drawCardForPlayer();

        assertEquals(1, game.getPlayerCards().size());

        assertEquals(initialDeckSize - 1, game.getDeck().getDeckSize());
    }


    @Test
    void testHandleDealerAcePoints() {
        game.addDealerPoints(22);
        game.addDealerAceCount();

        blackJackLogic.handleDealerAcePoints();

        assertEquals(12, game.getDealerPoints());
        assertEquals(0, game.getDealerAceCount());
    }


    @Test
    void testHandlePlayerAcePoints() {
        game.addPlayerPoints(22);
        game.addPlayerAceCount();

        blackJackLogic.handlePlayerAcePoints();

        assertEquals(12, game.getPlayerPoints());
        assertEquals(0, game.getPlayerAceCount());
    }


    @Test
    void testUpdatePlayerPointsWithAce() {
        Card aceCard = new Card("A", "♠", 0, 0, true);
        Card queenCard = new Card("Q", "♠", 0, 0, true);

        blackJackLogic.updatePlayerPoints(aceCard);
        blackJackLogic.updatePlayerPoints(queenCard);

        assertEquals(21, game.getPlayerPoints());
    }

    @Test
    void testUpdatePlayerPointsWithoutAce() {
        Card twoCard = new Card("2", "♠", 0, 0, true);
        Card queenCard = new Card("Q", "♠", 0, 0, true);

        blackJackLogic.updatePlayerPoints(twoCard);
        blackJackLogic.updatePlayerPoints(queenCard);

        assertEquals(12, game.getPlayerPoints());
    }


    @Test
    void testUpdateDealerPointsWithAce() {
        Card aceCard = new Card("A", "♠", 0, 0, true);
        Card fourCard = new Card("4", "♠", 0, 0, true);

        blackJackLogic.updateDealerPoints(aceCard);
        blackJackLogic.updateDealerPoints(fourCard);

        assertEquals(15, game.getDealerPoints());
    }

    @Test
    void testUpdateDealerPointsWithoutAce() {
        Card threeCard = new Card("3", "♠", 0, 0, true);
        Card kingnCard = new Card("K", "♠", 0, 0, true);

        blackJackLogic.updateDealerPoints(threeCard);
        blackJackLogic.updateDealerPoints(kingnCard);

        assertEquals(13, game.getDealerPoints());
    }



    @Test
    void testDecideWinner_PlayerWinsWith21() {
        when(mockGame.getPlayerPoints()).thenReturn(21);
        when(mockGame.getDealerPoints()).thenReturn(20);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Player", winner);
    }


    @Test
    void testDecideWinner_PlayerAndDealerWith21() {
        when(mockGame.getPlayerPoints()).thenReturn(21);
        when(mockGame.getDealerPoints()).thenReturn(21);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Player", winner);
    }


    @Test
    void testDecideWinner_DealerBust() {
        when(mockGame.getPlayerPoints()).thenReturn(18);
        when(mockGame.getDealerPoints()).thenReturn(22);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Player", winner);
    }


    @Test
    void testDecideWinner_PlayerBust() {
        when(mockGame.getPlayerPoints()).thenReturn(23);
        when(mockGame.getDealerPoints()).thenReturn(20);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Dealer", winner);
    }


    @Test
    void testDecideWinner_DealerWins() {
        when(mockGame.getPlayerPoints()).thenReturn(18);
        when(mockGame.getDealerPoints()).thenReturn(19);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Dealer", winner);
    }

    @Test
    void testDecideWinner_Tie() {
        when(mockGame.getPlayerPoints()).thenReturn(20);
        when(mockGame.getDealerPoints()).thenReturn(20);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Tie", winner);
    }


    @Test
    void testBetResult_PlayerWins() {
        when(mockGame.getPlayerPoints()).thenReturn(20);
        when(mockGame.getDealerPoints()).thenReturn(19);

        String winner = mockBlackJackLogic.decideWinner();

        assertEquals("Player", winner);
    }


    @Test
    void testFinishGame() {
        when(mockGame.getPlayerPoints()).thenReturn(21);
        when(mockGame.getDealerPoints()).thenReturn(18);
        doNothing().when(mockGame).setState("GAME_OVER");

        mockBlackJackLogic.finishGame();

        verify(mockGame).setWinner("Player");
        verify(mockGame).dealerFaceUp();
        verify(mockGame).setState("GAME_OVER");
    }


    @Test
    void testResetGame() {
        // using spy to observe behavior of our class
        doNothing().when(mockGame).resetGame();

        doNothing().when(spyBlackJackLogic).initializeGame();

        spyBlackJackLogic.resetGame();

        verify(mockGame).resetGame();

        verify(spyBlackJackLogic).initializeGame();
    }

    @Test
    void dealInitialCards() {
        doNothing().when(mockDeck).shuffle();

        mockBlackJackLogic.dealInitialCards();

        verify(mockGame, times(2)).addPlayerCard(any(Card.class));
        verify(mockGame, times(2)).addDealerCard(any(Card.class));
        verify(mockGame).dealerFaceDown();
    }

    @Test
    void increasePositionDealer(){
        Card firstCard = new Card("2", "♠", 0, 0, true);
        firstCard.setPosition(new Position(2, 4));

        Card secondCard = new Card("3", "♠", 0, 0, true);

        game.addDealerCard(firstCard);
        game.addDealerCard(secondCard);

        blackJackLogic.increasePositionDealer();

        Position firstCardPosition = game.getDealerCards().getFirst().getPosition();
        Position secondCardPosition = game.getDealerCards().getLast().getPosition();

        assertEquals(2, firstCardPosition.getX());
        assertEquals(4, firstCardPosition.getY());
        assertEquals(15, secondCardPosition.getX());
        assertEquals(4, secondCardPosition.getY());
    }

    @Test
    void increasePositionPlayer(){
        Card firstCard = new Card("2", "♠", 0, 0, true);
        firstCard.setPosition(new Position(2, 22));

        Card secondCard = new Card("3", "♠", 0, 0, true);

        game.addPlayerCard(firstCard);
        game.addPlayerCard(secondCard);

        blackJackLogic.increasePositionPlayer();

        Position firstCardPosition = game.getPlayerCards().getFirst().getPosition();
        Position secondCardPosition = game.getPlayerCards().getLast().getPosition();

        assertEquals(2, firstCardPosition.getX());
        assertEquals(22, firstCardPosition.getY());
        assertEquals(15, secondCardPosition.getX());
        assertEquals(22, secondCardPosition.getY());
    }

    @Test
    void testBetResultPlayerWins() {
        int betAmount = 100;
        int winResult = 200;

        when(mockGame.getPlayerPoints()).thenReturn(21);
        when(mockGame.getDealerPoints()).thenReturn(18);
        when(mockBetStrategy.winBet(betAmount)).thenReturn(winResult);

        mockBlackJackLogic.betResult(betAmount);

        verify(mockBetStrategy).winBet(betAmount);
        verify(mockGame).addPlayerTokens(winResult);
    }

    @Test
    void testBetResultDealerWins() {
        int betAmount = 100;
        int loseResult = -100;

        when(mockGame.getPlayerPoints()).thenReturn(18);
        when(mockGame.getDealerPoints()).thenReturn(20);
        when(mockBetStrategy.loseBet(betAmount)).thenReturn(loseResult);

        mockBlackJackLogic.betResult(betAmount);

        verify(mockBetStrategy).loseBet(betAmount);
        verify(mockGame).addPlayerTokens(loseResult);
    }

    @Test
    void testBetResult_Tie() {
        int betAmount = 100;
        int tieResult = 0;

        when(mockGame.getPlayerPoints()).thenReturn(20);
        when(mockGame.getDealerPoints()).thenReturn(20);
        when(mockBetStrategy.tieBet(betAmount)).thenReturn(tieResult);

        mockBlackJackLogic.betResult(betAmount);


        verify(mockBetStrategy).tieBet(betAmount);
        verify(mockGame).addPlayerTokens(tieResult);
    }


}
